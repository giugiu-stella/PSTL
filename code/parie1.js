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
var valeur = true 
console.log("faire une partie") ;
 var i =0 ;
while ( valeur ==true &&  i<22) //22 parties  22*3 =66 bits  
{          for (let j = 0 ; j<3 ; j++)  // un joueur a le droit a 3 lancers de dés 
          {
                var valeur_string = bit_aleatoir().toString()+ bit_aleatoir().toString() +bit_aleatoir().toString();  

                var index = codage.indexOf(valeur_string,0);
                
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

    i=i+3 ;
   console.log("faire une partie?") ;
}

