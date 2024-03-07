import React, { useState, useEffect } from 'react'
import Header from '../components/Header';
import {
    Button,
    Form,
    FormGroup,
  } from "react-bootstrap";
import axios from 'axios'
import { NavLink } from 'react-router-dom'
import { useNavigate } from 'react-router-dom'


export default function AddTarget() {

    const navigate = useNavigate();

    const [input, setInput] = useState({
        userAccountId: '',
        targetAccount: ''
    });

    const [userAccounts, setUserAccounts] = useState([]);
    const [isLoading, setLoading] = useState(true);

    useEffect(() => {
        axios
            .get('http://localhost:8080/accounts')
            .then((res) => {
                let insAcc = []
                res.data.forEach(data => {
                    insAcc.push({
                        'username': data.username,
                        'password': data.password,
                        'id': data.id
                    })
                })
                setUserAccounts(insAcc);
                console.log(insAcc);
                setLoading(false);
            })
            .catch((err) => console.log(err));
    }, [])

    // if (isLoading) {
    //     return <div data-testid="loading" className="App">Loading...</div>;
    // }

    const handleUserAccountInputChange = (e) => {
        e.persist();
	    setInput((inputs) => ({
		    ...inputs,
		    userAccountId: e.target.value
	    }));
    }

    const handleTargetAccountInputChange = (e) => {
        e.persist();
	    setInput((inputs) => ({
		    ...inputs,
		    targetAccount: e.target.value,
	    }));
    }

    const mappingAccountNames = () => {
        return userAccounts?.map((account) => (
                <option key={account.id} value={account.id}>{account.username}</option>
        ))
        
    }

    const submit = async () => {

        let data = JSON.stringify({
            "account": {
              "id": input.userAccountId
            },
            "username": input.targetAccount
        });

        let config = {
            method: 'post',
            maxBodyLength: Infinity,
            url: 'http://localhost:8080/targetAccount',
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

        navigate('/');
    }

    return (
        <div>
            <Header />
            <div className="bg-slate-300 p-20 px-100 flex flex-col h-[calc(100vh-75px)]">
                <div className="bg-white rounded-lg p-20 ">
                    <div className="text-3xl">
                        Add Target Account
                    </div>
                    <br></br><br></br>
                    <Form>

                        <FormGroup>
                            <Form.Label>Your Instagram Account </Form.Label>
                            <Form.Select type="text" 
                                id="course-term" 
                                value={input.userAccountId}
                                onChange={handleUserAccountInputChange}
                                placeholder="Select Your Account"
                                aria-label="Default select example">
                                <option>Select an Account</option>
                                {mappingAccountNames()}
                            </Form.Select>
                            <Form.Text id="passwordHelpBlock" muted>
                                Don't see your account? <NavLink to="/addaccount"> <u>Enter your account</u> </NavLink>
                            </Form.Text>
                        </FormGroup>
                        <br></br>

                        <FormGroup>
                            <Form.Label>Target Instagram Account </Form.Label>
                            <Form.Control type="text" 
                                id="course-title" 
                                value={input.targetAccount}
                                onChange={handleTargetAccountInputChange}
                                placeholder="Enter Target Account ID "
                                aria-label="Default select example">
                            </Form.Control>
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