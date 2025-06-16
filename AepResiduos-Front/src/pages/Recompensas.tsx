import { useState } from "react"
import { Gift, Loader2, CheckCircle } from "lucide-react"

export function Recompensas() {
  const recompensas = [
    { id: 1, titulo: "Caneca Sustentável", pontos: 100 },
    { id: 2, titulo: "Desconto na Coleta", pontos: 200 },
    { id: 3, titulo: "Camiseta Eco-Friendly", pontos: 300 },
    { id: 4, titulo: "Kit Reciclagem", pontos: 500 },
  ]

  const [loading, setLoading] = useState<number | null>(null)
  const [showModal, setShowModal] = useState(false)
  const [recompensaSelecionada, setRecompensaSelecionada] = useState<string | null>(null)

  function handleResgatar(id: number, titulo: string) {
    setLoading(id)

    setTimeout(() => {
      setLoading(null)
      setRecompensaSelecionada(titulo)
      setShowModal(true)
    }, 1500)
  }

  return (
    <div className="container mx-auto px-6 py-10">
      <h1 className="text-4xl font-bold mb-8 text-center dark:text-white flex items-center gap-2 justify-center">
        <Gift className="w-8 h-8 text-[#FACC15]" />
        Suas <span className="text-[#16A34A]">Recompensas</span>
      </h1>

      <p className="text-center text-lg text-gray-600 dark:text-gray-300 max-w-2xl mx-auto mb-10">
        Acumule pontos e troque por brindes, descontos e produtos sustentáveis.
        Contribuir com o meio ambiente nunca foi tão recompensador! ♻️
      </p>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
        {recompensas.map((item) => (
          <div
            key={item.id}
            className="bg-white dark:bg-[#1f2937] p-6 rounded-2xl shadow-md hover:shadow-xl transition flex flex-col items-center gap-4 group"
          >
            <Gift className="w-12 h-12 text-[#FACC15] group-hover:scale-110 transition" />
            <h2 className="text-2xl font-semibold dark:text-white">{item.titulo}</h2>
            <p className="text-gray-600 dark:text-gray-300">
              Pontos necessários:{" "}
              <span className="font-bold text-[#16A34A]">{item.pontos}</span>
            </p>
            <button
              disabled={loading === item.id}
              onClick={() => handleResgatar(item.id, item.titulo)}
              className={`w-full ${
                loading === item.id
                  ? "bg-[#15803d] opacity-80 cursor-not-allowed"
                  : "bg-[#16A34A] hover:bg-[#15803d]"
              } text-white px-6 py-3 rounded-full font-medium transition flex items-center justify-center gap-2`}
            >
              {loading === item.id ? (
                <>
                  <Loader2 className="w-5 h-5 animate-spin" />
                  Resgatando...
                </>
              ) : (
                "Resgatar"
              )}
            </button>
          </div>
        ))}
      </div>

      {/* Modal */}
      {showModal && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
          <div className="bg-white dark:bg-[#1f2937] rounded-xl p-8 max-w-sm w-full shadow-xl text-center">
            <CheckCircle className="w-14 h-14 text-[#16A34A] mx-auto mb-4" />
            <h2 className="text-2xl font-bold mb-2 dark:text-white">
              Recompensa Resgatada!
            </h2>
            <p className="text-gray-700 dark:text-gray-300 mb-6">
              Você resgatou: <span className="font-semibold">{recompensaSelecionada}</span>.
              Verifique seu e-mail ou acompanhe no Dashboard.
            </p>
            <button
              onClick={() => setShowModal(false)}
              className="bg-[#16A34A] text-white px-6 py-2 rounded-full hover:bg-[#15803d] transition"
            >
              Fechar
            </button>
          </div>
        </div>
      )}
    </div>
  )
}
