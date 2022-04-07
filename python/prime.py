from math import floor

print 'Test if number is prime; enter x <= 1 to exit.'
while 1:
    numstr = raw_input('Enter number to test: ')
    x = int(floor(float(numstr)))
    if x <= 1: break
    y = x / 2
    while y > 1:
        if x % y == 0:
            print x, 'has factor', y
            break
        y -= 1
    else:
        print x, 'is prime!'
