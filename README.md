# Daft

## Generating summaries NLG
- create instance of NLG class
-   NLG nlg = new NLG();
- call method generate summary to create a summary
- with message as argument; returns a message as string
-   nlg.generateSummary(message);





## For demonstration purposes only

### Generating summaries
- In NLG.java there is a main method which takes user input
- will ask what type of summary required. 
- type 1: group summary. All the summaries grouped into one
- type 2: individual summary. User will then be presented with different options of individual summaries availabe
- each of this will be sent to the sentence planner
- Then to the linguistic realisers
- sends sentence plan to the linguistic realiser
- prints the generated text 




### Verbalisation of Numbers
- Run Governor class
- Will ask for multiple inputs
- number and no, returns number without context
- number and yes, returns number in context. Additional inputs are the noun, the type of; Ordinal, (Epithet or Predicate) Cardinals
- The code will try to determine the grammatically correct manner to qualify that noun using that integer

#### example of a cardinal/epithet verbalisation
- Java Governor
- y
- 2 
- izinja
- E
Output (izinja ezimbili)

#### example of a ordinal verbalisation
- Java Governor
- y
- 2 
- isihlangu
- O
Output (isihlangu sesibili)

#### Example of a out of context number
-- Java Governor
-- 3162
-- n
Output(izinkulungwane eziyisitatu nekhulu namashumi ayisithupha nambili)




