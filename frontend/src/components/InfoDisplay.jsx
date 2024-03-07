import React, { useState, useEffect, useContext } from 'react'
import { Context } from "../pages/Main"
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import axios from 'axios';

const InfoDisplay = () => {

    const [targetUser] = useContext(Context);

    // [{date: yyyy-mm-dd, added: [], removed: []}, {...}]
    const [followerUpdate, setFollowerUpdate] = useState([]);
    const [followingUpdate, setFollowingUpdate] = useState([]);

    useEffect(() => {
        if (targetUser !== ''){
            getFollowerUpdates();
            getFollowingUpdates();
        }
    }, [targetUser])

    const getFollowerUpdates = async () => {
        let tmpFollowerUpdate = [];
        await axios.get('http://localhost:8080/followerUpdate/targetAccId/' + targetUser)
                .then((res) => {
                res.data.forEach((user) => {
                    let exists = tmpFollowerUpdate.some(obj => obj.date === user.date)

                    if (!exists){
                        let newObj = {
                            date: user.date,
                            added: [],
                            removed: []
                        };
                        tmpFollowerUpdate.push(newObj);
                    }
                    tmpFollowerUpdate.forEach(obj => {
                        if (obj.date === user.date){
                            if (user.added !== null){
                                obj.added.push(user.added);
                            }
                            if (user.removed !== null){
                                obj.removed.push(user.removed);
                            }
                        }
                    })
                })
                })
                .catch((err) => console.log(err));

        console.log(tmpFollowerUpdate);
        setFollowerUpdate(tmpFollowerUpdate);

    }

    const getFollowingUpdates = async () => {
        let tmpFollowingUpdate = [];
        await axios.get('http://localhost:8080/followingUpdate/targetAccId/' + targetUser)
                .then((res) => {
                res.data.forEach((user) => {
                    let exists = tmpFollowingUpdate.some(obj => obj.date === user.date)

                    if (!exists){
                        let newObj = {
                            date: user.date,
                            added: [],
                            removed: []
                        };
                        tmpFollowingUpdate.push(newObj);
                    }
                    tmpFollowingUpdate.forEach(obj => {
                        if (obj.date === user.date){
                            if (user.added !== null){
                                obj.added.push(user.added);
                            }
                            if (user.removed !== null){
                                obj.removed.push(user.removed);
                            }
                        }
                    })
                })
                })
                .catch((err) => console.log(err));

        console.log(tmpFollowingUpdate);
        setFollowingUpdate(tmpFollowingUpdate);

    }

    return (
        <Container>
            <Row className='h-[calc(50vh-72px)] bg-gray-200 rounded-lg my-4 p-4 overflow-auto'>
                {followerUpdate.map((day) => {
                    return (
                        <div className='bg-white rounded-lg mb-3 p-3'>
                            <div className='font-bold text-lg'>
                                {day.date}:
                            </div>
                            <ul>
                                {day.added.length !== 0 && 
                                <li>Followers added:
                                    <ul className='ml-6'>
                                    {day.added.map((added) => {
                                        return (
                                            <li>
                                                &#8226; <a href={'https://www.instagram.com/' + added} target="_blank">{added}</a>
                                            </li>
                                        )
                                    })}
                                    </ul>
                                </li>
                                }
                                {day.removed.length !== 0 && 
                                <li>Followers removed:
                                    <ul className='ml-6'>
                                    {day.removed.map((removed) => {
                                        return (
                                            <li>
                                                &#8226; <a href={'https://www.instagram.com/' + removed} target="_blank">{removed}</a>
                                            </li>
                                        )
                                    })}
                                    </ul>
                                </li>
                                }  
                            </ul>
                        </div>
                    )
                })}
            </Row>
            <Row className='h-[calc(50vh-72px)] bg-gray-200 rounded-lg my-4 p-4 overflow-auto'>
                {followingUpdate.map((day) => {
                    return (
                        <div className='bg-white rounded-lg mb-3 p-3'>
                            <div className='font-bold text-lg'>
                                {day.date}:
                            </div>
                            <ul>
                                {day.added.length !== 0 && 
                                <li>Started following:
                                    <ul className='ml-6'>
                                    {day.added.map((added) => {
                                        return (
                                            <li>
                                                &#8226; <a href={'https://www.instagram.com/' + added} target="_blank">{added}</a>
                                            </li>
                                        )
                                    })}
                                    </ul>
                                </li>
                                }
                                {day.removed.length !== 0 && 
                                <li>Stopped following:
                                    <ul className='ml-6'>
                                    {day.removed.map((removed) => {
                                        return (
                                            <li>
                                                &#8226; <a href={'https://www.instagram.com/' + removed} target="_blank">{removed}</a>
                                            </li>
                                        )
                                    })}
                                    </ul>
                                </li>
                                }  
                            </ul>
                        </div>
                    )
                })}
            </Row>
        </Container>
    )
}

export default InfoDisplay;