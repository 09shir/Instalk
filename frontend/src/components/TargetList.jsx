import React, {useState, useEffect, useContext} from 'react'
import { Context } from "../pages/Main"
import { FaUser } from "react-icons/fa";
import axios from 'axios'

const TargetList = () => {

    const [targetAccounts, setTargetAccounts] = useState([]);
    const [targetUser, setTargetUser] = useContext(Context);

    useEffect(() => {
        axios
            .get('http://localhost:8080/targetAccounts')
            .then((res) => {
                let targetAccounts = []
                res.data.forEach(data => {
                    targetAccounts.push({
                        'username': data.username,
                        'id': data.id
                    })
                })
                setTargetAccounts(targetAccounts);
            })
            .catch((err) => console.log(err));
    }, [])

    return (
        <>
            {targetAccounts.map(targetAccount => {
                return (
                    <div className='border-solid border-b-2 pl-5 pr-5 pt-2 pb-2 hover:bg-gray-200' >
                        <span className="userIcon pt-1 mr-2">
                            <FaUser />
                        </span>
                        <button className={`userName ${targetUser === targetAccount.id && 'font-bold'}`} onClick={() => setTargetUser(targetAccount.id)}>
                            {targetAccount.username}
                        </button>
                    </div>
                    
                )
            })}
        </>
    )
}

export default TargetList;