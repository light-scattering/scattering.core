#!/usr/bin/env node

import fs from 'fs';
import http from 'http';
import open from 'open';

const data = fs.readFileSync(0, 'utf-8');

if (!data || data.trim() === '') {
    console.error('Error: Could not read valid multisphere data from stdin.');
    process.exit(1);
}

const html = `
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>scatter-vis</title>
  <style>body { margin: 0; overflow: hidden; background-color: #ffffff; }</style>
  <script type="importmap">
    {
      "imports": {
        "three": "https://unpkg.com/three@0.160.0/build/three.module.js",
        "three/addons/": "https://unpkg.com/three@0.160.0/examples/jsm/"
      }
    }
  </script>
</head>
<body>
  <script type="module">
    import * as THREE from 'three';
    import { OrbitControls } from 'three/addons/controls/OrbitControls.js';

    const scene = new THREE.Scene();
    scene.background = new THREE.Color(0xffffff);

    const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 100000);
    const renderer = new THREE.WebGLRenderer({ antialias: true });

    renderer.setSize(window.innerWidth, window.innerHeight);

    document.body.appendChild(renderer.domElement);

    const controls = new OrbitControls(camera, renderer.domElement);
    
    const light = new THREE.DirectionalLight(0xffffff, 1);
    light.position.set(1, 1, 2).normalize();
    scene.add(light);
    scene.add(new THREE.AmbientLight(0x404040));

    const rawData = \`${data}\`;
    const lines = rawData.trim().split('\\n');
    
    const geometry = new THREE.SphereGeometry(1, 20, 20); // Base radius 1.0
    const material = new THREE.MeshPhongMaterial({ color: 0x4A90E2 });
    const instancedMesh = new THREE.InstancedMesh(geometry, material, lines.length);
    
    const dummy = new THREE.Object3D();
    let validCount = 0;

    lines.forEach(line => {
      const parts = line.trim().split(/\\s+/).map(Number);
      if (parts.length === 4) {
        dummy.position.set(parts[0], parts[1], parts[2]);
        dummy.scale.set(parts[3], parts[3], parts[3]);
        dummy.updateMatrix();
        instancedMesh.setMatrixAt(validCount++, dummy.matrix);
      }
    });

    instancedMesh.count = validCount;
    scene.add(instancedMesh);

    camera.position.z = 200;
    controls.update();

    function animate() {
      requestAnimationFrame(animate);
      renderer.render(scene, camera);
    }
    animate();

    window.addEventListener('resize', () => {
      camera.aspect = window.innerWidth / window.innerHeight;
      camera.updateProjectionMatrix();
      renderer.setSize(window.innerWidth, window.innerHeight);
    });
  </script>
</body>
</html>
`;

const server = http.createServer((req, res) => {
    res.writeHead(200, { 'Content-Type': 'text/html' });
    res.end(html);
});

server.listen(0, async () => {
    const port = server.address().port;
    const localUrl = `http://127.0.0.1:${port}`;
    
    console.log('\n=========================================');
    console.log(` 3D scatter-vis server running!`);
    console.log(` Address: ${localUrl}`);
    console.log('=========================================\n');

    try {
        await open(localUrl);
    } catch (err) {
    }
});