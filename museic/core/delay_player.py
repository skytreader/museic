from kivy.core.audio import SoundLoader

import sys
import time

class DelayPlayer(object):
    """
    Plays an mp3 file after a given delay.
    """
    
    def __init__(self, delay=4):
        """
        The delay is specified in seconds.
        """
        self.delay = delay

    def play(self, path):
        sound = SoundLoader.load(path)

        if sound:
            for i in range(self.delay):
                print "Playing in " + str(self.delay - i) + "s..."
                time.sleep(1)

            sound.play()


if __name__ == "__main__":
    
    if len(sys.argv) != 2:
        print "Usage: python " + sys.argv[0] + " <filepath>"
        exit(1)

    player = DelayPlayer()
    player.play(sys.argv[1])
