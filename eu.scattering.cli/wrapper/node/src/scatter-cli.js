#!/usr/bin/env node

import fs from 'fs';
import path from 'path';
import { spawn } from 'child_process';
import { fileURLToPath } from 'url';

const _nameFile = fileURLToPath(import.meta.url);
const _nameDir = path.dirname(_nameFile);

const _jarDir = path.join(_nameDir, '..', 'jars');
const _jarFiles = fs.readdirSync(_jarDir).filter(f => f.endsWith('.jar'));

if (_jarFiles.length === 0) {
    console.error('Error: scatter-cli jar not found.');
    process.exit(1);
}

const _jarPath = path.join(_jarDir, _jarFiles[0]);

const _args = process.argv.slice(2);

const _child = spawn('java', ['-jar', _jarPath, ..._args], {
    stdio: 'inherit'
});

_child.on('close', (code) => {
    process.exit(code);
});