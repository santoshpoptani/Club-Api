
import { BrowserRouter as Router , Routes, Route } from 'react-router-dom'
import Login from './components/Login'
import Register from './components/Register'
import Navigation from './components/Navigation'
import Home from './components/Home'
import Club from './components/Club'

function App() {
  
  return (
      <>
      <Router>
        <Navigation/>
        <Routes>
          <Route path='/login' element={<Login/>}></Route>
          <Route path='/register' element={<Register/>}></Route>
          <Route path='/' element={<Home/>}></Route>
          <Route path='/club/:id' element={<Club/>}></Route>
        </Routes>
      </Router>
      </>
  )
}

export default App
