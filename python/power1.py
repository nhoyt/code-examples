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

    j = 0
    while j < len(L):
        pow = 2 ** exp
        if L[j] == pow:
            print pow, 'found at index', j
            break
        else:
            j += 1
    else:
        print exp, 'not found'
