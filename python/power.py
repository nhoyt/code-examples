max = 11
print 'List of 2**exp with exp ranging from 0 through', max-1
L = map(lambda i: 2**i, range(max))
print L

while 1:
    exp = int(raw_input('Exponent: '))
    if exp < 0:
        print 'Bye!'
        break

    pow = 2 ** exp
    if pow in L:
        print pow, 'found at index', L.index(pow)
    else:
        print pow, 'not found'
