let codage= ["000","001","010", "011","100","101","110","111"]
function  bit_aleatoir() {
    min = Math.ceil(0);
    max = Math.floor(1);

var random =Math.floor( Math.random() * (max-min+1))+ min; 
return random;

}

const fs = require('fs')
var logger = fs.createWriteStream('Output.txt', {
    flags: 'a' // 'a'  (old data will be preserved)
})


for (let i = 0 ; i<22 ; i++)
{  
   var valeur_string = bit_aleatoir().toString()+ bit_aleatoir().toString() +bit_aleatoir().toString();
   var index = codage.indexOf(valeur_string,0);
   console.log(valeur_string,index)
   if (index==6)  { var valeur_dé= '1'; }
   else 
   {  if (index==7)   {var valeur_dé= '2';}
                    else  {
                        var valeur_dé= (index+1).toString() ;
                         }
      
   }
   logger.write(valeur_dé);
   logger.write('\n');
}