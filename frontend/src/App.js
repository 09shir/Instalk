import { Routes, Route } from "react-router-dom"
import './App.css';
import Main from './pages/Main';
import AddTarget from './pages/AddTarget';
import AddAccount from './pages/AddAccount';

function App() {
  return (
    <div className="background bg-slate-400">
      <Routes>
        <Route path="/" element={<Main />}/>
        <Route path="/addtarget" element={<AddTarget />}/>
        <Route path="/addaccount" element={<AddAccount />}/>
      </Routes>
    </div>
  );
}

export default App;
