import React, { useState, createContext } from 'react'
import Header from '../components/Header';
import TargetList from '../components/TargetList';
import InfoDisplay from '../components/InfoDisplay';
import ChartsDisplay from '../components/ChartsDisplay';
import AddTarget from '../components/AddTargetIcon';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

export const Context = createContext();

export default function Main() {
    const [targetUser, setTargetUser] = useState("");
    return (
        <div>
            <Header />
            <Context.Provider value={[targetUser, setTargetUser]}>
                <Container>
                    <Row>
                        <Col xs={3}>
                            <div className='bg-gray-300 flex flex-col h-[calc(100vh-123px)] w-full my-4 rounded-lg overflow-auto'>
                                <TargetList />
                                <div className="flex flex-col h-screen justify-end">
                                    <AddTarget />
                                </div>
                            </div>
                        </Col>
                        <Col xs={4}>
                            <div className='flex flex-col h-[calc(100vh-90px)] w-full'>
                                <InfoDisplay />
                            </div>
                        </Col>
                        <Col>
                            <div className='flex flex-col h-[calc(100vh-90px)] w-full'>
                                <ChartsDisplay />
                            </div>
                        </Col>
                    </Row>
                </Container>
            </Context.Provider>
        </div>
    )
}