# Dispatchers Example

This app demonstrates coroutine performance with different dispatchers in Jetpack Compose.

## Example 1
A message is updated 100,000 times.
This task is optimized by the Main dispatcher. 
The speed is such that the message cannot be updated. Only the final result is displayed.
Other dispatchers are slower.

## Example 2
Retrieve the content of a Wikipedia page. 
This task is optimized by the IO dispatcher.
The Main dispatcher does not allow making a web request: the task is fast but fails.
