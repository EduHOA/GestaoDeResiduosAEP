import { MapPin, PhoneCall, Landmark } from "lucide-react"
import { Link } from "react-router-dom"

export function Empresas() {
  const empresas = [
    {
      id: 1,
      nome: "EcoLógica Coletas",
      endereco: "Rua Verde, 123",
      telefone: "(44) 99999-0000",
    },
    {
      id: 2,
      nome: "Recicla Fácil",
      endereco: "Av. Sustentável, 456",
      telefone: "(44) 98888-1111",
    },
    {
      id: 3,
      nome: "Verde Vida",
      endereco: "Rua das Árvores, 789",
      telefone: "(44) 97777-2222",
    },
  ]

  return (
    <div className="container mx-auto px-6 py-10">
      <h1 className="text-4xl font-bold mb-8 text-center dark:text-white">
        Empresas <span className="text-[#16A34A]">Parceiras</span>
      </h1>

      <p className="text-center text-lg text-gray-600 dark:text-gray-300 max-w-2xl mx-auto mb-10">
        Conecte-se com empresas e cooperativas que atuam na sua região para facilitar a coleta de
        resíduos e contribuir com o meio ambiente. ♻️
      </p>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
        {empresas.map((empresa) => (
          <div
            key={empresa.id}
            className="bg-white dark:bg-[#1f2937] p-6 rounded-2xl shadow-md hover:shadow-xl transition group"
          >
            <div className="flex items-center gap-3 mb-4">
              <Landmark className="w-7 h-7 text-[#16A34A] group-hover:scale-110 transition" />
              <h2 className="text-2xl font-semibold dark:text-white">
                {empresa.nome}
              </h2>
            </div>

            <div className="flex items-center gap-3 mb-3">
              <MapPin className="w-5 h-5 text-[#FACC15]" />
              <p className="text-gray-700 dark:text-gray-300">{empresa.endereco}</p>
            </div>

            <div className="flex items-center gap-3 mb-6">
              <PhoneCall className="w-5 h-5 text-[#FACC15]" />
              <p className="text-gray-700 dark:text-gray-300">{empresa.telefone}</p>
            </div>

            <Link
              to={`/agendar?empresa=${encodeURIComponent(empresa.nome)}`}
              className="w-full bg-[#16A34A] text-white py-3 rounded-full hover:bg-[#15803d] transition font-medium text-center block"
            >
              Agendar Coleta
            </Link>
          </div>
        ))}
      </div>
    </div>
  )
}
