def countLines(file):
    file.seek(0)
    return len(file.readlines())

def countChars(file):
    file.seek(0)
    return len(file.read())

def test(fname):
    input = open(fname, 'r')
    print countLines(input), 'lines'
    print countChars(input), 'chars'
    input.close()

if __name__ == '__main__':
    print 'testing', __file__
    test(__file__)
