import subprocess
import sys
import os
import glob

def _get_jar():
    """Dynamically finds the single JAR file in the directory."""

    dir_base = os.path.dirname(os.path.abspath(__file__))
    dir_jars = os.path.join(dir_base, "jars")

    jars = glob.glob(os.path.join(dir_jars, "*.jar"))
    
    if not jars:
        raise FileNotFoundError(
            f"No JAR file found. The CLI binary is missing."
        )
        
    if len(jars) > 1:
        raise RuntimeError(
            f"Multiple JAR files found. Expected exactly one to avoid version conflicts."
        )
        
    return jars[0]

def run_cli():
    """Executes the Java CLI, passing along all command-line arguments."""
    
    command = ["java", "-jar", _get_jar()] + sys.argv[1:]
    
    try:
        result = subprocess.run(command)
        
        sys.exit(result.returncode)
    except FileNotFoundError:
        print("Error: 'java' is not installed or not in your system PATH.")
        sys.exit(1)
    except KeyboardInterrupt:
        sys.exit(130)

if __name__ == "__main__":
    run_cli()