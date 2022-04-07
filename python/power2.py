def get_int():
    i = int(raw_input('Exponent: '))
    return i

def init(lst):
    for i in range(9): lst.append(2 ** i)

L = []
init(L)
print L

while 1:
    exp = get_int()
    if exp < 0: break
    for pow in L:
        if pow == 2 ** exp:
            print 'found at index', L.index(pow)
            break
    else:
        print exp, 'not found'
