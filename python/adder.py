class Adder(object):
    def __init__(self, init):
        self.value = init
    def add(self, x):
        print 'Not implemented'

class ListAdder(Adder):
    def add(self, x):
        self.value += x
    def __add__(self, x):
        self.add(x)

class DictAdder(Adder):
    def add(self, x):
        for key in x:
            self.value[key] = x[key]
    def __add__(self, x):
        self.add(x)

