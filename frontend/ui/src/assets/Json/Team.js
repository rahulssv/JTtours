import React from 'react';
import rahul from '../Json/rahul.png';
import prathamesh from '../Json/prathamesh.png';
import taranjeet from '../Json/taranjeet.png';
import sandeep from '../Json/sandeep.png';
import harshit from '../Json/harshit.png';
import harsh from '../Json/harsh.png';
import arihant from '../Json/arihant.png';
import anand from '../Json/anand.png';
const arr = [ 
{
  name :"Rahul Vishwakarma",
  image:rahul,
  desc:"Software Engineer"
},
{
  name :"Prathamesh Moharkar",
  image:prathamesh,
  desc:"Software Engineer"
},
{
  name :'Sandeep Kumar',
  image:sandeep,
  desc:"Software Engineer"
},
{
    name :"Taranjeet Kalsi",
    image:taranjeet,
    desc:"Software Engineer"
},
{
  name:"Harshit Tiwari",
  image:harshit,
  desc:"Software Engineer"
},
{
  name:"Harsh",
  image:harsh,
  desc:"Software Engineer"
},
{
  name:"Arihant",
  image:arihant,
  desc:"Software Engineer"
},
{
  name:"Anand",
  image:anand,
  desc:"Software Engineer"
}
]

function Team() {
    
  return arr;
}

export default Team