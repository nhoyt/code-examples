def adder(a, b): return a + b

#print adder(1, 2)
#print adder(1.2, 3.4)
#print adder('1', '2')
#print adder([1,2,3], [4,5,6])

def genAdder(*args):
    return reduce((lambda x, y: x + y), args)

#print genAdder(1, 2, 3)
#print genAdder([1], [2,3], [4,5,6])
#print genAdder('spam')

def keyAdder1(good=4, bad=5, ugly=6):
    return good + bad + ugly

def keyAdder2(**args):
    sum = 0
    for x in args.keys():
        sum += args[x];
    return sum

def copyDict(dict):
    copy = {}
    for x in dict.keys(): copy[x] = dict[x]
    return copy

def addDict(d1, d2):
    new = {}
    for key in d1.keys(): new[key] = d1[key]
    for key in d2.keys(): new[key] = d2[key]
    return new

def f1(a, b): print a, b             # standard args
def f2(a, *b): print a, b            # positional varargs
def f3(a, **b): print a, b           # keyword varargs
def f4(a, *b, **c): print a, b, c    # mixed modes
def f5(a, b=2, c=3): print a, b, c   # defaults
def f6(a, b=2, *c): print a, b, c    # defaults and positional varargs
