import os, sys

if len(sys.argv) > 1:
    filename = sys.argv[1]

fullpath = os.path.abspath(filename)
print fullpath
