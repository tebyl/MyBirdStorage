package com.tebyl.aviariolocal.data

import java.text.SimpleDateFormat
import java.util.Locale

suspend fun seedIfEmpty(dao: BirdDao) {
    if (dao.count() > 0) return
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale("es"))

    @Suppress("SpellCheckingInspection")
    val birds = listOf(
        Bird(
            species = "Petirrojo europeo", sci = "Erithacus rubecula",
            photoUri = "https://images.unsplash.com/photo-1580827986698-9a7c4d9acc6f?w=600",
            dateMillis = sdf.parse("12 abr 2026")!!.time, dateStr = "12 abr 2026",
            location = "Bosque de los Robles", locShort = "Los Robles",
            behavior = "Cantaba desde una rama baja, muy territorial. Pausa breve entre frases melódicas.",
            notes = "Encontré al petirrojo a primera hora, cuando la niebla aún no se había levantado del todo. Registré dos frases distintas del canto.",
            tags = listOf("canto", "amanecer", "solitario"),
            isFavorite = true, lat = 40.4168, lng = -3.7038
        ),
        Bird(
            species = "Carbonero común", sci = "Parus major",
            photoUri = "https://images.unsplash.com/photo-1444464666168-49d633b86797?w=600",
            dateMillis = sdf.parse("10 abr 2026")!!.time, dateStr = "10 abr 2026",
            location = "Jardín de casa", locShort = "Jardín",
            behavior = "Visitó el comedero repetidamente. Dominante frente al herrerillo.",
            notes = "Siempre llega el primero al comedero. Hoy trajo a una pareja. La hembra es más apagada.",
            tags = listOf("jardín", "comedero", "pareja"),
            isFavorite = false, lat = 40.4180, lng = -3.7020
        ),
        Bird(
            species = null, sci = null,
            photoUri = "https://images.unsplash.com/photo-1560982588-fa8bff85f17f?w=600",
            dateMillis = sdf.parse("08 abr 2026")!!.time, dateStr = "08 abr 2026",
            location = "Laguna del Mirador", locShort = "Laguna",
            behavior = "Zancuda de mediano tamaño. Plumaje gris con pecho moteado.",
            notes = "Fotografié desde lejos con el móvil. Imagen borrosa. Pendiente de identificar. ¿Correlimos?",
            tags = listOf("agua", "zancuda", "sin-identificar"),
            isFavorite = false, lat = 40.4090, lng = -3.7150
        ),
        Bird(
            species = "Mirlo común", sci = "Turdus merula",
            photoUri = "https://images.unsplash.com/photo-1611689342806-0863700ce1e4?w=600",
            dateMillis = sdf.parse("05 abr 2026")!!.time, dateStr = "05 abr 2026",
            location = "Parque Alameda", locShort = "Alameda",
            behavior = "Macho adulto buscando lombrices tras la lluvia de la noche.",
            notes = "Pico y anillo ocular amarillo muy intenso. Muy confiado, se acercó a menos de dos metros.",
            tags = listOf("suelo", "lluvia", "macho"),
            isFavorite = true, lat = 40.4200, lng = -3.7060
        ),
        Bird(
            species = "Jilguero europeo", sci = "Carduelis carduelis",
            photoUri = "https://images.unsplash.com/photo-1459862365747-ad7b5b94e504?w=600",
            dateMillis = sdf.parse("02 abr 2026")!!.time, dateStr = "02 abr 2026",
            location = "Camino de los Cardos", locShort = "Cardos",
            behavior = "Grupo de ocho individuos comiendo semillas de cardo.",
            notes = "Bandada muy activa y difícil de fotografiar. El canto es muy musical, casi flautado.",
            tags = listOf("bandada", "canto", "semillas"),
            isFavorite = false, lat = 40.4050, lng = -3.6980
        ),
        Bird(
            species = "Herrerillo común", sci = "Cyanistes caeruleus",
            photoUri = "https://images.unsplash.com/photo-1516127487600-a28a4d303494?w=600",
            dateMillis = sdf.parse("28 mar 2026")!!.time, dateStr = "28 mar 2026",
            location = "Jardín de casa", locShort = "Jardín",
            behavior = "Acróbata en las ramas del manzano. Azul brillante en la luz de la tarde.",
            notes = "Llegó después del carbonero. Menos tiempo en el comedero, prefiere los extremos de las ramas.",
            tags = listOf("jardín", "acróbata", "azul"),
            isFavorite = true, lat = 40.4180, lng = -3.7025
        ),
        Bird(
            species = null, sci = null,
            photoUri = "https://images.unsplash.com/photo-1549608276-5786777e6587?w=600",
            dateMillis = sdf.parse("25 mar 2026")!!.time, dateStr = "25 mar 2026",
            location = "Mirador del río", locShort = "Río",
            behavior = "Vuelo recto y bajo sobre el agua. Alas azuladas o verdes muy vívidas.",
            notes = "Pasó muy rápido río abajo. Posible martín pescador por los colores. Necesito confirmar.",
            tags = listOf("agua", "vuelo", "sin-identificar"),
            isFavorite = false, lat = 40.4130, lng = -3.7100
        ),
        Bird(
            species = "Cernícalo vulgar", sci = "Falco tinnunculus",
            photoUri = "https://images.unsplash.com/photo-1591608971362-f08b2a75731a?w=600",
            dateMillis = sdf.parse("20 mar 2026")!!.time, dateStr = "20 mar 2026",
            location = "Campos de la dehesa", locShort = "Dehesa",
            behavior = "Cernido perfecto durante cuarenta segundos antes de caer en picado.",
            notes = "Macho adulto con cabeza gris y dorso pardo rojizo. Impresionante la precisión del cernido contra el viento.",
            tags = listOf("rapaz", "cernido", "macho"),
            isFavorite = true, lat = 40.3990, lng = -3.7200
        ),
        Bird(
            species = "Gorrión común", sci = "Passer domesticus",
            photoUri = "https://images.unsplash.com/photo-1565611183870-c9f53e31b74c?w=600",
            dateMillis = sdf.parse("18 mar 2026")!!.time, dateStr = "18 mar 2026",
            location = "Plaza del pueblo", locShort = "Plaza",
            behavior = "Colonia numerosa bajo los aleros de la iglesia. Bullicio constante.",
            notes = "Siempre presentes. Hoy les tiré unas migas y en segundos llegaron veinte.",
            tags = listOf("urbano", "colonia", "bullicio"),
            isFavorite = false, lat = 40.4210, lng = -3.6990
        ),
        Bird(
            species = "Pinzón vulgar", sci = "Fringilla coelebs",
            photoUri = "https://images.unsplash.com/photo-1522926193341-e9ffd686c60f?w=600",
            dateMillis = sdf.parse("15 mar 2026")!!.time, dateStr = "15 mar 2026",
            location = "Bosque de los Robles", locShort = "Los Robles",
            behavior = "Macho en plumaje nupcial. Canto fuerte y repetido desde lo alto del roble.",
            notes = "Primer pinzón de la temporada. Los colores están muy vivos. La cabeza gris azulada reluce.",
            tags = listOf("canto", "bosque", "macho"),
            isFavorite = false, lat = 40.4165, lng = -3.7042
        )
    )
    birds.forEach { dao.upsert(it) }
}
