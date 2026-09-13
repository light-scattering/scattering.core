import sys
import numpy as np
import pyvista as pv

def run_vis():

    try:
        data = np.loadtxt(sys.stdin)
    except Exception as e:
        print("Error: Could not read valid multisphere (X Y Z R) data from stdin.", file=sys.stderr)
        sys.exit(1)

    if data.size == 0 or data.shape[1] != 4:
        print("Error: Input must be in 4-column multisphere (X Y Z R) format.", file=sys.stderr)
        sys.exit(1)

    points = data[:, :3]
    radii = data[:, 3]

    cloud = pv.PolyData(points)
    cloud["radii"] = radii

    spheres = cloud.glyph(scale="radii", geom=pv.Sphere(radius=1.0, theta_resolution=20, phi_resolution=20), orient=False)

    plotter = pv.Plotter()
    plotter.add_mesh(spheres, color="#4A90E2", smooth_shading=True)
    plotter.set_background("white")
    plotter.show()

if __name__ == "__main__":
    run_vis()