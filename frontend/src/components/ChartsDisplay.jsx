import React, { useState, useEffect, useContext } from 'react'
import { Context } from "../pages/Main"
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend,
  } from 'chart.js';
import { Line } from 'react-chartjs-2';
import axios from 'axios'
import { dateDifference } from './functions/datesFunctions';

ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend
  );

const ChartsDisplay = () => {

    const [targetUser] = useContext(Context);
    // [{date: yyyy-mm-dd, changed: num}, {...}]
    const [followerUpdate, setFollowerUpdate] = useState([]);
    const [followingUpdate, setFollowingUpdate] = useState([]);

    const [followerLabels, setFollowerLabels] = useState([]);
    const [followingLabels, setFollowingLabels] = useState([]);
    
    useEffect(() => {
        if (targetUser !== ''){
            getFollowerUpdates();
            getFollowingUpdates();
        }
    }, [targetUser])

    const getFollowerUpdates = async () => {
        let tmpFollowerUpdate = [];
        let tmpLabels = [];
        await axios.get('http://localhost:8080/followerUpdate/targetAccId/' + targetUser)
                .then((res) => {
                res.data.forEach((user) => {
                    
                    let exists = tmpFollowerUpdate.some(obj => obj.date === user.date)

                    if (!exists){
                        let newObj = {
                            date: user.date,
                            changed: 0,
                        };
                        tmpFollowerUpdate.push(newObj);
                        tmpLabels.push(user.date);
                    }
                    tmpFollowerUpdate.forEach(obj => {
                        if (obj.date === user.date){
                            if (user.added !== null){
                                obj.changed++;
                            }
                            if (user.removed !== null){
                                obj.changed--;
                            }
                        }
                    })
                })
                })
                .catch((err) => console.log(err));

        console.log(tmpFollowerUpdate);
        setFollowerUpdate(tmpFollowerUpdate.reverse());
        setFollowerLabels(tmpLabels.reverse());
    }

    const getFollowingUpdates = async () => {
        let tmpFollowingUpdate = [];
        let tmpLabels = [];
        await axios.get('http://localhost:8080/followingUpdate/targetAccId/' + targetUser)
                .then((res) => {
                res.data.forEach((user) => {
                    
                    let exists = tmpFollowingUpdate.some(obj => obj.date === user.date)

                    if (!exists){
                        let newObj = {
                            date: user.date,
                            changed: 0,
                        };
                        tmpFollowingUpdate.push(newObj);
                        tmpLabels.push(user.date);
                    }
                    tmpFollowingUpdate.forEach(obj => {
                        if (obj.date === user.date){
                            if (user.added !== null){
                                obj.changed++;
                            }
                            if (user.removed !== null){
                                obj.changed--;
                            }
                        }
                    })
                })
                })
                .catch((err) => console.log(err));

        setFollowingUpdate(tmpFollowingUpdate.reverse());
        setFollowingLabels(tmpLabels.reverse());
    }

    const follower_options = {
        responsive: true,
        plugins: {
          legend: {
            position: 'top',
          },
          title: {
            display: true,
            text: 'Followers Line Chart',
          },
        },
      };

    const following_options = {
        responsive: true,
        plugins: {
          legend: {
            position: 'top',
          },
          title: {
            display: true,
            text: 'Followings Line Chart',
          },
        },
      };

    const follower_data = {
        labels: followerLabels,
        datasets: [
          {
            label: '△ of followers',
            data: followerUpdate.map((date) => {
                return date.changed
            }),
            borderColor: 'rgb(53, 162, 235)',
            backgroundColor: 'rgba(255, 99, 132, 0.5)',
          }
        ],
      };

    const following_data = {
        labels: followingLabels,
        datasets: [
          {
            label: '△ of followings',
            data: followingUpdate.map((date) => {
                return date.changed
            }),
            borderColor: 'rgb(255, 99, 132)',
            backgroundColor: 'rgba(255, 99, 132, 0.5)',
          }
        ],
      };

    return (
        <Container>
            <Row className='h-[calc(50vh-72px)] bg-gray-100 rounded-lg my-4 p-2'>
                {targetUser !== '' && <Line options={follower_options} data={follower_data} />}
            </Row>
            <Row className='h-[calc(50vh-72px)] bg-gray-100 rounded-lg my-4'>
                {targetUser !== '' && <Line options={following_options} data={following_data} />}
            </Row>
        </Container>
    )
}

export default ChartsDisplay;