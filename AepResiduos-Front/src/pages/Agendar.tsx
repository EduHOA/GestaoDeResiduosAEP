import { useLocation } from "react-router-dom"
import { useState, type FormEvent } from "react"
import { toast } from "react-hot-toast"
import { CalendarCheck, MapPin, Landmark, Loader2, CheckCircle } from "lucide-react"
import { useScheduling } from "../contexts/SchedulingContext"
import { useAuth } from "../contexts/AuthContext"
import { useNavigate } from "react-router-dom"

export function Agendar() {
  const location = useLocation()
  const navigate = useNavigate()
  const { user } = useAuth()
  const { addScheduling } = useScheduling()
  const params = new URLSearchParams(location.search)
  const empresaSelecionada = params.get("empresa")

  const [empresa, setEmpresa] = useState(empresaSelecionada || "")
  const [endereco, setEndereco] = useState("")
  const [data, setData] = useState("")
  const [showModal, setShowModal] = useState(false)
  const [loading, setLoading] = useState(false)

  async function handleSubmit(e: FormEvent) {
    e.preventDefault()

    if (!user) {
      toast.error("Você precisa estar logado para agendar uma coleta!")
      navigate("/login")
      return
    }

    if (!empresa || !endereco || !data) {
      toast.error("Preencha todos os campos!")
      return
    }

    setLoading(true)

    try {
      addScheduling({
        empresa,
        endereco,
        data,
      })

      toast.success("Agendamento realizado com sucesso!")
      setShowModal(true)
      setEmpresa("")
      setEndereco("")
      setData("")
    } catch (error) {
      toast.error("Erro ao realizar agendamento")
      console.error(error)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="container mx-auto px-6 py-10">
      <h1 className="text-4xl font-bold mb-8 dark:text-white flex items-center gap-2">
        <CalendarCheck className="w-8 h-8 text-[#16A34A]" />
        Agendar Coleta
      </h1>

      <form
        onSubmit={handleSubmit}
        className="bg-white dark:bg-[#1f2937] p-8 rounded-xl shadow-md max-w-xl mx-auto space-y-6"
      >
        <div>
          <label className="block mb-1 font-medium dark:text-white flex items-center gap-2">
            <Landmark className="w-5 h-5 text-[#16A34A]" />
            Selecione a Empresa
          </label>
          <select
            value={empresa}
            onChange={(e) => setEmpresa(e.target.value)}
            className="w-full border border-gray-300 dark:border-gray-600 dark:bg-[#111827] dark:text-white p-3 rounded"
          >
            <option value="">Selecione</option>
            <option>EcoLógica Coletas</option>
            <option>Recicla Fácil</option>
            <option>Verde Vida</option>
          </select>
        </div>

        <div>
          <label className="block mb-1 font-medium dark:text-white flex items-center gap-2">
            <MapPin className="w-5 h-5 text-[#FACC15]" />
            Endereço para Coleta
          </label>
          <input
            type="text"
            placeholder="Digite seu endereço completo"
            value={endereco}
            onChange={(e) => setEndereco(e.target.value)}
            className="w-full border border-gray-300 dark:border-gray-600 dark:bg-[#111827] dark:text-white p-3 rounded"
          />
        </div>

        <div>
          <label className="block mb-1 font-medium dark:text-white flex items-center gap-2">
            <CalendarCheck className="w-5 h-5 text-[#16A34A]" />
            Data da Coleta
          </label>
          <input
            type="date"
            value={data}
            onChange={(e) => setData(e.target.value)}
            min={new Date().toISOString().split('T')[0]}
            className="w-full border border-gray-300 dark:border-gray-600 dark:bg-[#111827] dark:text-white p-3 rounded"
          />
        </div>

        <button
          type="submit"
          disabled={loading}
          className={`w-full ${
            loading ? "bg-[#15803d] opacity-80 cursor-not-allowed" : "bg-[#16A34A] hover:bg-[#15803d]"
          } text-white py-3 rounded-full font-medium transition flex justify-center items-center gap-2`}
        >
          {loading ? (
            <>
              <Loader2 className="w-5 h-5 animate-spin" />
              Agendando...
            </>
          ) : (
            "Confirmar Agendamento"
          )}
        </button>
      </form>

      {/* Modal */}
      {showModal && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
          <div className="bg-white dark:bg-[#1f2937] rounded-xl p-8 max-w-sm w-full shadow-xl text-center">
            <CheckCircle className="w-14 h-14 text-[#16A34A] mx-auto mb-4" />
            <h2 className="text-2xl font-bold mb-2 dark:text-white">
              Agendamento Realizado!
            </h2>
            <p className="text-gray-700 dark:text-gray-300 mb-6">
              Sua coleta foi agendada com sucesso. A empresa entrará em contato em breve.
            </p>
            <div className="flex gap-4 justify-center">
              <button
                onClick={() => setShowModal(false)}
                className="bg-gray-200 text-gray-800 px-6 py-2 rounded-full hover:bg-gray-300 transition"
              >
                Fechar
              </button>
              <button
                onClick={() => {
                  setShowModal(false)
                  navigate('/dashboard')
                }}
                className="bg-[#16A34A] text-white px-6 py-2 rounded-full hover:bg-[#15803d] transition"
              >
                Ver Meus Agendamentos
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}
