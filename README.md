# Description

This is a simple fix for the issue discovered in UAF 0.8.3d, where it would be impossible to change the behavior of Neuko officers - while the displayed behavior changed, they kept acting as if they were still Fearless.

## Performance

There is no performance impact, as the method from this fix is called once at the beginning of the battle, once for every ship, and the checks are very light.


## Credits

All credit goes to the amazing team behind United Aurora Federation mod, I'm just standing on the shoulders of giants.