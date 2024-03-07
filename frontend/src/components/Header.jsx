import React from 'react'
import Navbar from 'react-bootstrap/Navbar'
import Container from 'react-bootstrap/Container';
import NavDropdown from 'react-bootstrap/NavDropdown';
import Nav from 'react-bootstrap/Nav';
import icon from '../assets/icon.png';

const Header = () => {
    return (
        <div>
            <Navbar bg="light">
            <Container>
                <Navbar.Brand href="/">
                    <img width='200' height='50' src={icon} className="d-inline-block align-top" alt="logo"/>
                </Navbar.Brand>
                <Nav expand="lg" className="me-auto"></Nav>
                <Nav expand="lg" className="me-auto"></Nav>
                <Nav expand="lg" className="me-auto"></Nav>
                <Nav expand="lg" className="me-auto"></Nav>
                <Nav expand="lg" className="me-auto"></Nav>
                <Nav expand="lg" className="me-auto"></Nav>
                <Nav expand="lg" className="me-auto">
                    <Nav.Link href="https://www.sfu.ca/" target="_blank">About</Nav.Link>
                    <NavDropdown title="Contact" id="navbarScrollingDropdown">
                        <NavDropdown.Item href="https://www.github.com/09shir" target="_blank">
                            GitHub
                        </NavDropdown.Item>
                        <NavDropdown.Item href="https://www.linkedin.com/in/roy-sh1" target="_blank">
                            LinkedIn
                        </NavDropdown.Item>
                        <NavDropdown.Divider />
                        <NavDropdown.Item href="mailto: rsa180@sfu.ca" target="_blank">
                            Email
                        </NavDropdown.Item>
                    </NavDropdown>
                </Nav>
            </Container>
        </Navbar>
        </div>
    )
}

export default Header;