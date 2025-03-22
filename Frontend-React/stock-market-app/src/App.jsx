import { useState } from 'react'
import './App.css'
import StockUpdates from './components/StockUpdates.jsx'

function App() {

  return (
    <>
      <div>
        <h1> Stock Market Dashboard</h1>
        <StockUpdates />
      </div>
    </>
  )
}

export default App
