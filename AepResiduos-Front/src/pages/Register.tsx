import { Link, useNavigate } from "react-router-dom"
import type { FormEvent, ChangeEvent } from "react"
import { toast } from "react-hot-toast"
import { useState } from "react"
import { registerUser } from "../api/auth"
import { useAuth } from "../contexts/AuthContext"

export function Register() {
  const navigate = useNavigate()
  const { login } = useAuth()
  const [isLoading, setIsLoading] = useState(false)
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    phone: "",
    address: ""
  })

  function formatPhoneNumber(value: string) {
    // Remove todos os caracteres não numéricos
    const numbers = value.replace(/\D/g, "")
    
    // Aplica a máscara (xx) xxxxx-xxxx
    if (numbers.length <= 2) {
      return numbers
    } else if (numbers.length <= 7) {
      return `(${numbers.slice(0, 2)}) ${numbers.slice(2)}`
    } else {
      return `(${numbers.slice(0, 2)}) ${numbers.slice(2, 7)}-${numbers.slice(7, 11)}`
    }
  }

  function handleInputChange(e: ChangeEvent<HTMLInputElement>) {
    const { name, value } = e.target
    
    if (name === 'phone') {
      const formattedPhone = formatPhoneNumber(value)
      setFormData(prev => ({ ...prev, [name]: formattedPhone }))
    } else {
      setFormData(prev => ({ ...prev, [name]: value }))
    }
  }

  async function handleSubmit(e: FormEvent) {
    e.preventDefault()
    setIsLoading(true)

    // Validação da senha
    if (formData.password.length < 8) {
      toast.error("A senha deve ter pelo menos 8 caracteres")
      setIsLoading(false)
      return
    }

    // Remove formatação do telefone antes de enviar
    const dataToSend = {
      ...formData,
      phone: formData.phone.replace(/\D/g, "") // Remove todos os caracteres não numéricos
    }

    try {
      console.log('Iniciando tentativa de registro com dados:', { ...dataToSend, password: '***' })
      const response = await registerUser(dataToSend)
      console.log('Registro bem sucedido, redirecionando...')
      
      // Salva os dados do usuário no contexto
      login(response.user, response.token)
      
      toast.success("Conta criada com sucesso!")
      navigate("/")
    } catch (error) {
      console.error('Erro detalhado:', error)
      if (error instanceof Error) {
        if (error.message.includes('Failed to fetch')) {
          toast.error("Não foi possível conectar ao servidor. Verifique se o backend está rodando.")
        } else if (error.message.includes('403')) {
          toast.error("Acesso negado. Verifique se os dados estão corretos.")
        } else {
          toast.error(error.message)
        }
      } else {
        toast.error("Erro ao criar conta. Tente novamente.")
      }
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <div className="flex items-center justify-center min-h-screen bg-[#F9FAFB] dark:bg-[#111827]">
      <div className="bg-white dark:bg-[#1f2937] p-8 rounded-2xl shadow-md w-full max-w-md">
        <h1 className="text-3xl font-bold mb-8 text-center dark:text-white">
          Criar <span className="text-[#16A34A]">Conta</span>
        </h1>

        <form className="space-y-6" onSubmit={handleSubmit}>
          <div>
            <label className="block mb-2 font-medium dark:text-white">Nome</label>
            <input
              type="text"
              name="name"
              value={formData.name}
              onChange={handleInputChange}
              placeholder="Seu nome completo"
              className="w-full border border-gray-300 dark:border-gray-600 dark:bg-[#111827] dark:text-white p-3 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#16A34A] transition"
              required
            />
          </div>

          <div>
            <label className="block mb-2 font-medium dark:text-white">E-mail</label>
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
            <label className="block mb-2 font-medium dark:text-white">Endereço</label>
            <input
              type="text"
              name="address"
              value={formData.address}
              onChange={handleInputChange}
              placeholder="Seu Logradouro"
              className="w-full border border-gray-300 dark:border-gray-600 dark:bg-[#111827] dark:text-white p-3 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#16A34A] transition"
              required
            />
          </div>

          <div>
            <label className="block mb-2 font-medium dark:text-white">Telefone</label>
            <input
              type="tel"
              name="phone"
              value={formData.phone}
              onChange={handleInputChange}
              placeholder="(xx) xxxxx-xxxx"
              maxLength={15}
              className="w-full border border-gray-300 dark:border-gray-600 dark:bg-[#111827] dark:text-white p-3 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#16A34A] transition"
              required
            />
          </div>

          <div>
            <label className="block mb-2 font-medium dark:text-white">
              Senha <span className="text-sm text-gray-500">(mínimo 8 caracteres)</span>
            </label>
            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleInputChange}
              placeholder="********"
              minLength={8}
              className="w-full border border-gray-300 dark:border-gray-600 dark:bg-[#111827] dark:text-white p-3 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#16A34A] transition"
              required
            />
          </div>

          <button
            type="submit"
            disabled={isLoading}
            className="w-full bg-[#16A34A] text-white py-3 rounded-full font-medium hover:bg-[#15803d] transition disabled:opacity-50 disabled:cursor-not-allowed"
          >
            {isLoading ? "Criando conta..." : "Criar Conta"}
          </button>
        </form>

        <p className="text-center text-sm text-gray-600 dark:text-gray-300 mt-6">
          Já tem uma conta?{" "}
          <Link
            to="/login"
            className="text-[#16A34A] font-medium hover:underline"
          >
            Fazer Login
          </Link>
        </p>
      </div>
    </div>
  )
}
