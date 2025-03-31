package polyglot.a05b

import polyglot.a05b.Logics
import util.Sequences.*

/**
 * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita,
 * che realizza una semplice animazione, che effettua uno scatto ad ogni pressione del pulsante ">", mostrata nella
 * sequenza delle immagini fig1.png, fig2.png, fig3.png, fig4.png,..
 * 1 - all'inizio la griglia è vuota, con una cella attiva (con asterisco) scelta in modo random *non nel bordo*
 * 2 - ad ogni pressione si attivano 8 celle in più, in direzione orizzontale/verticale/diagonale rispetto a
 *     quella iniziale, a distanza via via crescente
 * 3 - quando l'aggiunta di una cella porterebbe a uscire dalla griglia, si esca
 *
 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento
 * della totalità del punteggio:
 * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
 * - compilazione e esecuzione dell'esercizio da linea di comando (I COMANDI DEVONO ESSERE GIA' PRONTI)
 *
 * La classe GUIExample fornita include codice che potrebbe essere utile per la soluzione.
 *
 * Indicazioni di punteggio:
 * - correttezza della parte obbligatoria: 10 punti
 * - qualità della parte opzionale: 5 punti
 * - compilazione/esecuzione da linea di comando: 2 punti
 */

class LogicsImpl(private val size: Int) extends Logics:
  private case class Point2D(x: Int, y: Int)
  private var cycle: Int = 0
  private val isValidPositionFunction: (Int, Point2D, Point2D) => Boolean =
    (i, start, actual) => ((actual.x - start.x).abs == (actual.y - start.y).abs ||
      (actual.y - start.y) == 0 ||
      (actual.x - start.x) == 0) &&
      (actual.y - start.y).abs <= i &&
      (actual.x - start.x).abs <= i
  private val rand = scala.util.Random(44)
  private val startPoint: Point2D = Point2D(rand.nextInt(size),
    rand.nextInt(size))

  override def tick(): Unit = cycle = cycle + 1

  override def isOver: Boolean =
    var result = false
    for
      x <- -1 until size + 1
      y <- -1 until size + 1
      if x == -1 || x == size || y == -1 || y == size
    do result = isValidPositionFunction(cycle, startPoint, Point2D(x, y)) || result
    result

  override def hasElement(x: Int, y: Int): Boolean =
    isValidPositionFunction(cycle, startPoint, Point2D(x, y))
