# RandomAndGreedyApproach
Greedy approach game

# Game
Creating a game with (customized) chess pieces.
• Players start with a predetermined amount of gold. 
• There will be many options (different hero) for each piece type.
• Spend your gold on pieces. A higher level does not mean a higher value select based on Gold and Attack.
• Maximize the total attack points of your pieces. 

# Greedy Approach
The Greedy approach builds an army by picking the unit with the highest "Attack points / gold" ratio for which gold is sufficient for each turn. It returns an array containing all units and saves the most efficient unit in another array at each step.

# Random Approach
The Random approach chooses a random troop at each step and assembles an army. At each step, it returns an array holding all units, picks a random unit, and saves it in a different string. However, if the amount of gold owned is not enough for the selected unit, it chooses a different unit.
