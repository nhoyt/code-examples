def sieve(n):
    """
    find primes up to n by Sieve of Eratosthenes

    """
    primes = [2]
    marked = [0] * n
    for p in primes:
        i = p
        while i < n:
            marked[i] = 1
            i += p
        for q in range(p, n):
            if marked[q] == 0:
                primes.append(q)
                break
    return primes

if __name__ == '__main__':
    n = None
    while not n:
        try:
            n = int(raw_input('Value for n: '))
        except ValueError:
            print 'Invalid Number'

    print sieve(n)
