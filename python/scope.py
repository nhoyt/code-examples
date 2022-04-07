x = 4 # global scope

def f(x): return x*x

def g(x): return f(x)

print g(x)
