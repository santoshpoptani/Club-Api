
import { BrowserRouter as Router , Routes, Route } from 'react-router-dom'
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Login from './components/Login'
import Register from './components/Register'
import Navigation from './components/Navigation'
import Home from './components/Home'
import Club from './components/Club'
import Events from './components/Events'
import EventDetail from './components/EventDetail'
import Profile from './components/Profile'

function App() {
  
  return (
      <>
      <Router>
        <Navigation/>
        <ToastContainer />
        <Routes>
          <Route path='/login' element={<Login/>}></Route>
          <Route path='/register' element={<Register/>}></Route>
          <Route path='/' element={<Home/>}></Route>
          <Route path='/club/:id' element={<Club/>}></Route>
          <Route path='/events' element={<Events/>}></Route>
          <Route path='/event/:id' element={<EventDetail/>}></Route>
          <Route path='profile' element={<Profile/>}></Route>
        </Routes>
      </Router>
      </>
  )
}

export default App
