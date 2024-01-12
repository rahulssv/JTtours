import React, { useRef, useState } from 'react';
// Import Swiper React components
import { Swiper, SwiperSlide } from 'swiper/react';
import obj from "../../assets/Json/info.json";
import Team from "../../assets/Json/Team.js";

// Import Swiper styles
import 'swiper/css';
import 'swiper/css/pagination';
import 'swiper/css/navigation';

import './about.css';

// import required modules
import {Autoplay, Pagination, Navigation } from 'swiper/modules';

export default function AboutUs() {
  const team = Team();
  return (
    <div className='container-fluid p-4 mt-4 text-center' style={{width:'80%',background:'white'}}> <h2>Our Team</h2>
      <Swiper
        slidesPerView={1}
        spaceBetween={30}
        loop={true}
        pagination={{
          clickable: true,
        }}
        id='aboutus'
        autoplay={{
            delay: 1500,
            disableOnInteraction: false,
          }}
        navigation={true}
        modules={[Pagination, Navigation,Autoplay]}
        className="mySwiper"
      >
         {
            team.map((member)=>{
                return (<SwiperSlide className='p-4'>
                <div className='text-center'>
                    <div style={{width:'200px',height:'200px',overflow:'hidden',display:'flex',alignItems:'center',justifyContent:'center',borderRadius:'50%' }} className='mb-4 shadow'>
                        <img style={{width:'100%',height:'100%'}} src={member.image} alt="team-member" />
                    </div>
                    <h4>{member.name}</h4>
                    <h6>{member.desc}</h6>
                    {/* <p>UI</p> */}
                </div>
            </SwiperSlide>)
            })
         }
         
        
      </Swiper>
    </div>
  );
}
