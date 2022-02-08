# Animal Evolution Simulation
This project is a simple simulation showing predator-prey cycles(they are actualy herbivore-plant cycles but it works similarly). Additionaly simulation contains evolution of animals.  

## Map
There are two diffrent maps on which animals are living. Each map is a rectangle made of square fields. Animals are standing only on one field but there may be many animals on one filed. Maps have diffrent rules. First map allows animals to hop-over the border so when animal after move would go out of the map it just jumps to filed on the other side of the map. Second map desn't allow that so when animal bumps into border it just losses move and nothing happens.

## Animal
Animal is our herbivore. It has few simple statistics representing it's current state.

Most important for the simulation is **genotype**. **Genotype** is a list of 32 numbers from 0 to 7. It's so important because animals makes deciosion about moves based on it. Animal also has **energy**, this statistic states whether animal frist of all is alvie and if it can procreate. Last one is **direction**, it says what should animal do thogeter with **genotype**.

## Moves
Each turn animal makes semi-random choice on what to do. It's semi-random because as stated before decision is based on animal's **genotype** and **direction**. 
Each number means diffrent move `0` is a step `forward`, `4` is a step `backward` (based on **direction**). Other numbers are changing animal direction using forumla ``number * 45°``(animal turns right).

After all animals make a move stro
