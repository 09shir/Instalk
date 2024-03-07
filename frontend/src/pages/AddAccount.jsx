import React, { useState } from 'react'
import Header from '../components/Header';
import {
    Button,
    Form,
    FormGroup,
  } from "react-bootstrap";
import axios from 'axios';
import { useNavigate } from 'react-router-dom'

export default function AddAccount() {

    const [input, setInput] = useState({
        userAccount: '',
        userPassword: ''
    });

    const navigate = useNavigate();

    const handleUserAccountInputChange = (e) => {
        e.persist();
	    setInput((inputs) => ({
		    ...inputs,
		    userAccount: e.target.value,
	    }));
    }

    const handleUserPasswordInputChange = (e) => {
        e.persist();
	    setInput((inputs) => ({
		    ...inputs,
		    userPassword: e.target.value,
	    }));
    }

    const submit = async () => {
        console.log(input)

        let data = JSON.stringify({
            "username": input.userAccount,
            "password": input.userPassword
        });

        let config = {
            method: 'post',
            maxBodyLength: Infinity,
            url: 'http://localhost:8080/account',
            headers: { 
              'Content-Type': 'application/json'
            },
            data : data
        };

        await axios
            .request(config)
            .then((response) => {
                console.log(JSON.stringify(response.data));
            })
            .catch((error) => {
                console.log(error);
            });
        
            navigate('/addtarget');
    }

    return (
        <div>
            <Header />
            <div className="bg-slate-200 p-20 px-100 flex flex-col h-[calc(100vh-75px)]">
                <div className="bg-white rounded-lg p-20 ">
                    <div className="text-3xl">
                        Add Your Account
                    </div>
                    <br></br><br></br>
                    <Form>
                        <FormGroup>
                            <Form.Label>Your Instagram Account </Form.Label>
                            <Form.Control type="text" 
                                id="course-title" 
                                value={input.userAccount}
                                onChange={handleUserAccountInputChange}
                                placeholder="Enter Account ID "
                                aria-label="Default select example">
                            </Form.Control>
                        </FormGroup>
                        <br></br>

                        <FormGroup>
                        <Form.Label>Your Instagram Password </Form.Label>
                            <Form.Control type="password" 
                                id="course-title" 
                                value={input.userPassword}
                                onChange={handleUserPasswordInputChange}
                                placeholder="Enter Password"
                                aria-label="Default select example">
                            </Form.Control>
                            <Form.Text id="passwordHelpBlock" muted>
                                We'll never access or share your password with anyone else.
                            </Form.Text>
                        </FormGroup>
                        
                    </Form>
                    <br></br>
                    <Button variant="outline-secondary" onClick={submit}> 
                        Add
                    </Button>
                </div>
            </div>
            
        </div>
    )
}