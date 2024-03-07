import { IoAddCircleSharp } from "react-icons/io5";
import { NavLink } from 'react-router-dom'

const AddTarget = () => {
    return (
        <NavLink to="/addtarget" className="ml-2 mb-2 items-end absolute cursor-pointer">
            <IoAddCircleSharp onClick={() => {console.log('lol')}} size={40} />
        </NavLink>
    )
}

export default AddTarget;