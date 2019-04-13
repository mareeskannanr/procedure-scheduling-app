import React from 'react';
import { Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';

const MessageModal = props => (
    <Modal isOpen={true}>
        <ModalHeader>
            <span className='fas fa-thumbs-up'></span> Success
        </ModalHeader>
        <ModalBody>
            {
                props.type === 'status' ? <h4>Status Updated Successfully!</h4> : <h4>Schedule Planned Successfully!</h4>
            }
        </ModalBody>
        <ModalFooter>
            <div className='col-12 text-center'>
                <button type='button' className='btn btn-danger' onClick={() => props.closeModal()}>
                    <span className='fas fa-times'></span> Close
                </button>
            </div>
        </ModalFooter>
    </Modal>    
);

export default MessageModal;