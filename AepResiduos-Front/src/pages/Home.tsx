import { Leaf, MapPin, CalendarCheck, Gift } from "lucide-react"
import { Link } from "react-router-dom"

export function Home() {
  return (
    <div>
      {/* Hero */}
      <section className="bg-white dark:bg-[#111827] py-20">
        <div className="container mx-auto px-6 flex flex-col-reverse md:flex-row items-center gap-10">
          <div className="flex-1">
            <h1 className="text-5xl font-bold mb-6 leading-tight dark:text-white">
              Transforme <span className="text-[#16A34A]">Resíduos</span> em <span className="text-[#FACC15]">Impacto</span>.
            </h1>
            <p className="text-lg text-gray-600 dark:text-gray-300 mb-8 max-w-lg">
              Agende coletas, encontre pontos de reciclagem e contribua para um futuro mais sustentável e consciente.
            </p>
            <div className="flex flex-wrap gap-4">
              <Link
                to="/agendar"
                className="bg-[#16A34A] text-white px-8 py-3 rounded-full font-medium hover:bg-[#15803d] transition"
              >
                Agendar Coleta
              </Link>
              <Link
                to="/empresas"
                className="border border-[#16A34A] text-[#16A34A] px-8 py-3 rounded-full font-medium hover:bg-[#16A34A] hover:text-white transition"
              >
                Ver Empresas
              </Link>
            </div>
          </div>
          <div className="flex-1">
            <img
              src="/residuos.png"
              alt="Reciclagem de resíduos"
              className="w-full max-w-sm mx-auto"
            />
          </div>
        </div>
      </section>

      {/* Funcionalidades */}
      <section className="py-20 bg-[#F9FAFB] dark:bg-[#1f2937]">
        <div className="container mx-auto px-6">
          <h2 className="text-4xl font-bold mb-12 text-center dark:text-white">
            O que você pode fazer?
          </h2>
          <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
            <div className="bg-white dark:bg-[#111827] rounded-2xl shadow-md hover:shadow-xl p-6 text-center transition group">
              <Leaf className="w-12 h-12 mx-auto text-[#16A34A] group-hover:scale-110 transition" />
              <h3 className="text-xl font-semibold mt-4 mb-2 dark:text-white">
                Conscientize
              </h3>
              <p className="text-gray-600 dark:text-gray-300">
                Aprenda como contribuir para um meio ambiente mais limpo e sustentável.
              </p>
            </div>
            <div className="bg-white dark:bg-[#111827] rounded-2xl shadow-md hover:shadow-xl p-6 text-center transition group">
              <MapPin className="w-12 h-12 mx-auto text-[#FACC15] group-hover:scale-110 transition" />
              <h3 className="text-xl font-semibold mt-4 mb-2 dark:text-white">
                Encontre Pontos
              </h3>
              <p className="text-gray-600 dark:text-gray-300">
                Descubra empresas e locais de coleta próximos de você.
              </p>
            </div>
            <div className="bg-white dark:bg-[#111827] rounded-2xl shadow-md hover:shadow-xl p-6 text-center transition group">
              <CalendarCheck className="w-12 h-12 mx-auto text-[#16A34A] group-hover:scale-110 transition" />
              <h3 className="text-xl font-semibold mt-4 mb-2 dark:text-white">
                Agende Coletas
              </h3>
              <p className="text-gray-600 dark:text-gray-300">
                Programe coletas rápidas e práticas diretamente pela plataforma.
              </p>
            </div>
            <div className="bg-white dark:bg-[#111827] rounded-2xl shadow-md hover:shadow-xl p-6 text-center transition group">
              <Gift className="w-12 h-12 mx-auto text-[#FACC15] group-hover:scale-110 transition" />
              <h3 className="text-xl font-semibold mt-4 mb-2 dark:text-white">
                Ganhe Recompensas
              </h3>
              <p className="text-gray-600 dark:text-gray-300">
                Acumule pontos e troque por recompensas exclusivas por ajudar o planeta.
              </p>
            </div>
          </div>
        </div>
      </section>
    </div>
  )
}
