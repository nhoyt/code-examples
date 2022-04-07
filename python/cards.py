"""
Print sequences of 13 cards, each of which is based on one of the first NUM_SEQS
card values. Each sequence is built by skipping over a set number of cards (zero
or more), controlled by the incr parameter, while moving through the CARD_VALUES
and wrapping around to the beginning as is necessary to include all 13 cards.
"""
CARD_VALUES = ('A', '2', '3', '4', '5', '6', '7', '8', '9', '10', 'J', 'Q', 'K')
NUM_CARDS = 13
NUM_SEQS = 4

def sequence(incr):
    cards = []
    for i in range(NUM_CARDS):
        index = i + ((i + 1) * incr)
        cards.append(CARD_VALUES[index % NUM_CARDS])
    return cards

def print_sequences():
    sequences = []
    for i in range(NUM_SEQS):
        sequences.append(sequence(i))

    for j in range(NUM_CARDS):
        for i in range(NUM_SEQS):
            print "%2s " % sequences[i][j],
        print

if __name__ == "__main__":
    print_sequences()
