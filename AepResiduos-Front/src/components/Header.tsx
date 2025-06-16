import { Link, useNavigate } from "react-router-dom"
import { Moon, Sun, LogOut, User } from "lucide-react"
import { useEffect, useState } from "react"
import { useAuth } from "../contexts/AuthContext"
import { toast } from "react-hot-toast"
import { logoutUser } from "../api/auth"

export function Header() {
  const navigate = useNavigate()
  const { user, isAuthenticated, logout } = useAuth()
  const [isLoggingOut, setIsLoggingOut] = useState(false)
  const [darkMode, setDarkMode] = useState(() => {
    return localStorage.getItem("theme") === "dark"
  })

  useEffect(() => {
    if (darkMode) {
      document.documentElement.classList.add("dark")
      localStorage.setItem("theme", "dark")
    } else {
      document.documentElement.classList.remove("dark")
      localStorage.setItem("theme", "light")
    }
  }, [darkMode])

  const handleLogout = () => {
    logout()
    toast.success('Logout realizado com sucesso!')
    navigate('/login')
  }

  return (
    <header className="bg-white dark:bg-[#1f2937] shadow-sm sticky top-0 z-50 transition">
      <div className="container mx-auto px-6 py-4 flex justify-between items-center">
        <h1 className="text-2xl font-bold text-[#16A34A]">
          <Link to="/">
            AEP<span className="text-[#FACC15]">Resíduos</span>
          </Link>
        </h1>

        <nav className="hidden md:flex gap-8">
          <Link to="/" className="hover:text-[#16A34A] transition">
            Home
          </Link>
          <Link to="/empresas" className="hover:text-[#16A34A] transition">
            Empresas
          </Link>
          <Link to="/agendar" className="hover:text-[#16A34A] transition">
            Agendar
          </Link>
          <Link to="/dashboard" className="hover:text-[#16A34A] transition">
            Dashboard
          </Link>
          <Link to="/sobre" className="hover:text-[#16A34A] transition">
            Sobre
          </Link>
          <Link to="/recompensas" className="hover:text-[#16A34A] transition">
            Recompensas
          </Link>
        </nav>

        <div className="flex items-center gap-4">
          <button
            onClick={() => setDarkMode(!darkMode)}
            className="p-2 rounded hover:bg-gray-100 dark:hover:bg-gray-700 transition"
          >
            {darkMode ? (
              <Sun className="w-5 h-5 text-yellow-400" />
            ) : (
              <Moon className="w-5 h-5 text-gray-800 dark:text-white" />
            )}
          </button>

          {isAuthenticated ? (
            <div className="flex items-center gap-4">
              <div className="flex items-center gap-2 text-gray-700 dark:text-gray-300">
                <User className="w-5 h-5" />
                <span className="font-medium">{user?.name}</span>
              </div>
              <button
                onClick={handleLogout}
                className="flex items-center gap-2 text-red-500 hover:text-red-600 transition disabled:opacity-50 disabled:cursor-not-allowed"
              >
                <LogOut className="w-5 h-5" />
                <span className="hidden md:inline">
                  {isLoggingOut ? "Saindo..." : "Sair"}
                </span>
              </button>
            </div>
          ) : (
            <>
              <Link
                to="/login"
                className="border border-[#16A34A] text-[#16A34A] px-5 py-2 rounded-full font-medium hover:bg-[#16A34A] hover:text-white transition"
              >
                Login
              </Link>

              <Link
                to="/register"
                className="bg-[#16A34A] text-white px-5 py-2 rounded-full font-medium hover:bg-[#15803d] transition"
              >
                Registro
              </Link>
            </>
          )}
        </div>
      </div>
    </header>
  )
}
