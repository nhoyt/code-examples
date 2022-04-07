class Squares:
    def __init__(self, start, stop):
        Squares.start = start
        Squares.stop = stop
    def __iter__(self):                   # get iterator object
        self.value = Squares.start - 1
        return self
    def next(self):                       # on each for iteration
        if self.value == Squares.stop:
            raise StopIteration
        self.value += 1
        return self.value ** 2
