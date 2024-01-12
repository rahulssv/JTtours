import React, {useEffect, useState} from 'react';
import Modal from 'react-bootstrap/Modal';
import {RotatingLines} from 'react-loader-spinner';

function PaymentModel({show,setShow, handleClose,donePayment,desc}) {
     
  return (
    <div> 
        <Modal show={show} onHide={handleClose}>
         
        <Modal.Body className='' style={{height:'400px',display:'flex',flexDirection:'column', alignItems:'center',justifyContent:"center"}}> 
            {
                donePayment ?(<div><i style={{fontSize:'80px',color:'green'}} class="bi bi-check-circle-fill"></i></div>): (<RotatingLines strokeColor='#003D59' strokeWidth='5' animationDuration='0.75' width='120' visible={true} />)
            }
             
            <h4 className='mt-5 text-center'>{desc}</h4>
             
        </Modal.Body>
      </Modal>
    </div>
  )
}

export default PaymentModel