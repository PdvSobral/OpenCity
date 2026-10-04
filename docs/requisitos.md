# Requisitos Funcionais
## APP
 - Criação de reports:
 	- Tag GPS ou Colocação manual no mapa
 	- Upload de foto (com strip de metadados)
 	- Descrição/Legenda
 	- Tipo/Categoria
 - Num report criado:
 	- \[User\] confirmar/desconfirmar validade (válido) do report
 	- deixar comentários
 	- \[admin\] atualizar estado
 - Mapa (default activity)
 	- Top Down
 	- Pinos nos reports
 	- Capacidade de Filtração
 	- Botão Markers (Chama Activity.Settings.MarkerManagement)

 - (optional) Path Tracing

 - Menu Hamburger
	 - Settings:
	 	- Dark/Light Mode
	 	- Animações On/Off
	 	- Notifications
	 		- Quando a X distância de um marker
	 		- Quando acima de Severidade X
	 		- Quando um report em que o user participou:
	 			- Muda estado
	 			- recebe um update
	 			- é terminado
 		- App Language (translations)
	 	- Mapa
		 	- Capacidade para fazer download de mapas
	 		- Modo Orientação \[Preso Norte, Manual, Sensor Bússola\]
	 		- (optional) Icons
	 	- Report
	 		- \[admin\]
	 	- Gestão Markers
	 		- Criação/Edição/Remoção
	 		- guardar localmente ou sincronizar com server
	 		- (optional) icons/cor
	 	- Conta
			- Sistema de Pontos:
			 	- associado a uma conta
			 	- ganha x por problema reportado
	 		- Imagem de Perfil
	 		- (optional) Nome
	 		- Username
	 		- Login
	 			- Pelo menos dois tipos de contas + anónimo (admin e user) (anónimo só pode ver, não reportar)
	 			- (a definir) aprovação
		 		- Password
			 		- (Optional) 2FA/MFA
				- Google Login
			- Terminar Sessão
			- Apagar Conta
			- Privacidade
				- Pedir todos os dados relativamentes ao utilizador
				- Ver Termos de Utilização
				- Política de Privacidade
		- Servidor (para mudar é necessário logout)
			- endereço (IP/porta)
			- password (pode ser aberto)
 	- \[admin\] Painel de gestão
 	- Botão Markers (Chama Activity.Settings.MarkerManagement)
 	- Botão Filtragem
 	- (optional) Statistics showing which areas have the most problems (tipo heatmap)
 	- Lista/Histórico de reports em que contribuiu
 		- Export reports as CSV or JSON


## SERVIDOR
	- Capacidade para sincronizar com outros servidores
	- Base de dados
	- Sistema de Upload
	- APIRest

## MISC
 - OpenStreetMap or Overpass API for map data and nearby infrastructure (OpenStreetMap’s Overpass API can query map objects such as roads, public facilities and other geographic features.)

# Requisitos Não Funcionais
 - Uso Kotlin
 - Ser FOSS
 - Nome: CityPulse
