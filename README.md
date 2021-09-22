# Daft

## Generating summaries NLG
- create instance of NLG class
-   NLG nlg = new NLG();
- call method generate summary to create a summary
- with message as argument; returns a message as string
-   nlg.generateSummary(message);

### Example of generating a summaries (delete)
- In NLG.java
- Main method gets messages from message collection (other messages commented out, uncomment message to generate summary about it)
- sends them to the sentence planner
- sends sentence plan to the linguistic realiser
- prints the generated text 

#### Things to discuss with amy
- Translation of categories, subcategories, months
- last minute adding of rules if necessary
- does she want to call generate summary once or multiple times, once will cause problem of distinguishing between messages
- if amy happy, refactor code, comment  and generate javadocs



## Verbalisation of Numbers
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




