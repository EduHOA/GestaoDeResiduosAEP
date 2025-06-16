import { Link, useNavigate } from "react-router-dom"
import type { FormEvent, ChangeEvent } from "react"
import { toast } from "react-hot-toast"
import { useState } from "react"
import { loginUser } from "../api/auth"
import { useAuth } from "../contexts/AuthContext"

export function Login() {
  const navigate = useNavigate()
  const { login } = useAuth()
  const [isLoading, setIsLoading] = useState(false)
  const [formData, setFormData] = useState({
    email: "",
    password: ""
  })

  function handleInputChange(e: ChangeEvent<HTMLInputElement>) {
    const { name, value } = e.target
    setFormData(prev => ({ ...prev, [name]: value }))
  }

  async function handleSubmit(e: FormEvent) {
    e.preventDefault()
    setIsLoading(true)

    try {
      console.log('Iniciando tentativa de login...')
      const response = await loginUser(formData.email, formData.password)
      console.log('Login bem sucedido, redirecionando...')
      
      // Salva os dados do usuário no contexto
      login(response.user, response.token)
      
      toast.success("Login realizado com sucesso!")
      navigate("/")
    } catch (error) {
      console.error('Erro detalhado:', error)
      if (error instanceof Error) {
        if (error.message.includes('Failed to fetch')) {
          toast.error("Não foi possível conectar ao servidor. Verifique se o backend está rodando.")
        } else {
          toast.error(error.message)
        }
      } else {
        toast.error("Erro ao fazer login. Tente novamente.")
      }
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <div className="flex items-center justify-center min-h-screen bg-[#F9FAFB] dark:bg-[#111827]">
      <div className="bg-white dark:bg-[#1f2937] p-8 rounded-2xl shadow-md w-full max-w-md">
        <h1 className="text-3xl font-bold mb-8 text-center dark:text-white">
          Entrar na <span className="text-[#16A34A]">Plataforma</span>
        </h1>

        <form className="space-y-6" onSubmit={handleSubmit}>
          <div>
            <label className="block mb-2 font-medium dark:text-white">
              E-mail
            </label>
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleInputChange}
              placeholder="seuemail@email.com"
              className="w-full border border-gray-300 dark:border-gray-600 dark:bg-[#111827] dark:text-white p-3 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#16A34A] transition"
              required
            />
          </div>

          <div>
            <label className="block mb-2 font-medium dark:text-white">
              Senha
            </label>
            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleInputChange}
              placeholder="********"
              className="w-full border border-gray-300 dark:border-gray-600 dark:bg-[#111827] dark:text-white p-3 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#16A34A] transition"
              required
            />
          </div>

          <button
            type="submit"
            disabled={isLoading}
            className="w-full bg-[#16A34A] text-white py-3 rounded-full font-medium hover:bg-[#15803d] transition disabled:opacity-50 disabled:cursor-not-allowed"
          >
            {isLoading ? "Entrando..." : "Entrar"}
          </button>
        </form>

        <p className="text-center text-sm text-gray-600 dark:text-gray-300 mt-6">
          Não tem uma conta?{" "}
          <Link
            to="/register"
            className="text-[#16A34A] font-medium hover:underline"
          >
            Cadastre-se
          </Link>
        </p>
      </div>
    </div>
  )
}
