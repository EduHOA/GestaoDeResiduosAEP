import { Leaf, MapPin, Rocket, Users } from "lucide-react"

export function Sobre() {
  return (
    <div className="container mx-auto px-6 py-10">
      <h1 className="text-4xl font-bold mb-8 text-center dark:text-white">
        Sobre o <span className="text-[#16A34A]">AEP Resíduos</span>
      </h1>

      <p className="text-lg text-center text-gray-700 dark:text-gray-300 max-w-3xl mx-auto mb-12">
        Somos uma plataforma que conecta pessoas, empresas e cooperativas com o objetivo de criar um
        futuro mais sustentável. Aqui, você pode <b>agendar coletas</b>, <b>encontrar pontos de reciclagem</b>
        e ainda <b>ganhar recompensas</b> ajudando o meio ambiente.
      </p>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
        {/* Card 1 */}
        <div className="bg-white dark:bg-[#1f2937] rounded-xl shadow-md p-6 flex gap-4 items-start">
          <Leaf className="w-10 h-10 text-[#16A34A]" />
          <div>
            <h2 className="text-2xl font-semibold mb-2 dark:text-white">Missão</h2>
            <p className="text-gray-700 dark:text-gray-300">
              Promover a conscientização ambiental, incentivando práticas de reciclagem e descarte correto de resíduos.
            </p>
          </div>
        </div>

        {/* Card 2 */}
        <div className="bg-white dark:bg-[#1f2937] rounded-xl shadow-md p-6 flex gap-4 items-start">
          <MapPin className="w-10 h-10 text-[#FACC15]" />
          <div>
            <h2 className="text-2xl font-semibold mb-2 dark:text-white">Impacto Local</h2>
            <p className="text-gray-700 dark:text-gray-300">
              Facilitamos a conexão entre moradores e empresas de coleta, fortalecendo o impacto positivo na comunidade.
            </p>
          </div>
        </div>

        {/* Card 3 */}
        <div className="bg-white dark:bg-[#1f2937] rounded-xl shadow-md p-6 flex gap-4 items-start">
          <Rocket className="w-10 h-10 text-[#16A34A]" />
          <div>
            <h2 className="text-2xl font-semibold mb-2 dark:text-white">Visão</h2>
            <p className="text-gray-700 dark:text-gray-300">
              Ser referência em soluções sustentáveis, contribuindo para cidades mais limpas e conscientes.
            </p>
          </div>
        </div>

        {/* Card 4 */}
        <div className="bg-white dark:bg-[#1f2937] rounded-xl shadow-md p-6 flex gap-4 items-start">
          <Users className="w-10 h-10 text-[#FACC15]" />
          <div>
            <h2 className="text-2xl font-semibold mb-2 dark:text-white">Comunidade</h2>
            <p className="text-gray-700 dark:text-gray-300">
              Incentivamos a colaboração entre usuários, cooperativas e empresas para gerar um impacto coletivo significativo.
            </p>
          </div>
        </div>
      </div>
    </div>
  )
}
