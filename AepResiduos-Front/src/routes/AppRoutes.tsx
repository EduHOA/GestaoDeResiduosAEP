import { Routes, Route, useLocation } from "react-router-dom"
import { Home } from "../pages/Home"
import { Empresas } from "../pages/Empresas"
import { Agendar } from "../pages/Agendar"
import { Dashboard } from "../pages/Dashboard"
import { Login } from "../pages/Login"
import { Register } from "../pages/Register"
import { Sobre } from "../pages/Sobre"
import { Recompensas } from "../pages/Recompensas"
import { Header } from "../components/Header"

export function AppRoutes() {
  const location = useLocation()
  const hideHeader = ["/login", "/register"].includes(location.pathname)

  return (
    <div className="bg-[#F9FAFB] text-[#111827] dark:bg-[#111827] dark:text-white min-h-screen">
      {!hideHeader && <Header />}
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/empresas" element={<Empresas />} />
        <Route path="/agendar" element={<Agendar />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/sobre" element={<Sobre />} />
        <Route path="/recompensas" element={<Recompensas />} />
      </Routes>
    </div>
  )
}

