import javax.swing.*
import javax.swing.border.*
import javax.swing.event.*
import javax.swing.text.*
import java.awt.*


class NumberField() extends JTextField, DocumentListener {

    val origForeground = this.getForeground()

    getDocument().addDocumentListener(this)

    override def changedUpdate(e: DocumentEvent): Unit = 
        getText() match
            case null => ()
            case s =>
                val base = if (s.startsWith("0x")) 16 else 10
                val numStr = if (s.startsWith("0x")) s.substring(2) else s
                
                try
                    Integer.parseInt(numStr, base)
                    setForeground(origForeground)
                catch
                    case e: Exception => setForeground(Color.red)

    override def insertUpdate(e: DocumentEvent): Unit = changedUpdate(e)
    override def removeUpdate(e: DocumentEvent): Unit = changedUpdate(e)
}


def fieldPanelFactory(fields: Seq[(String, Component)]): JPanel =
    if (fields.isEmpty) throw new IllegalArgumentException(s"fieldPanelFactory fields arg is empty.")
    val (theseFields, nextFields) =
        if (fields(0)(1).isInstanceOf[JTextField] || fields(0)(1).isInstanceOf[JComboBox[?]])
            val firstFatComponentIndex = fields.indexWhere{
                case (_, comp: JTextField) => false
                case (_, comp: JComboBox[?]) => false
                case _ => true
            }
            if (firstFatComponentIndex == -1)
                (fields, Nil)
            else
                (fields.take(firstFatComponentIndex), fields.drop(firstFatComponentIndex))
        else
            (fields.take(1), fields.tail)

    val p = new JPanel()
    p.setLayout(new GridLayout(0, 2))
    p.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8))
    theseFields.foreach{ case (text, comp) =>
        p.add(new JLabel(text))
        p.add(comp)
    }

    val outer = new JPanel(new BorderLayout())
    outer.add(p, BorderLayout.NORTH)
    if (!nextFields.isEmpty)
        outer.add(fieldPanelFactory(nextFields))
    outer


class TopPanel(c: Component, nextC: Component = null) extends JPanel {
    setLayout(new BorderLayout())
    add(c, BorderLayout.NORTH)
    Option(nextC).foreach(c2 => add(c2))
}

class LabeledPanel(text: String, c: Component) extends JPanel {
    setLayout(new BorderLayout())
    setBorder(
        BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED, Color.BLACK, Color.GRAY), text)
    )
    add(c)
}

class SwitchablePanel(panels: Seq[(String, Component)]) extends JPanel {
    var radios = IndexedSeq[JRadioButton]()

    setLayout(new BorderLayout())

    val group = new ButtonGroup()

    val cardLayout = new CardLayout()
    val cardPanel = new JPanel(cardLayout)

    val radioPanel = new JPanel()
    panels.foreach(p => {
        val radioButton = new JRadioButton(p._1, p == panels.head)
        radioButton.setEnabled(false)
        radios = radios :+ radioButton
        radioButton.setActionCommand(p._1)
        radioButton.addActionListener(e => cardLayout.show(cardPanel, e.getActionCommand()))
        group.add(radioButton)
        radioPanel.add(radioButton)
    })
    add(radioPanel, BorderLayout.NORTH)

    panels.foreach(p => cardPanel.add(new TopPanel(p._2), p._1))

    add(cardPanel);

    def selectRadio(n: Int) =
        radios(n).setSelected(true)
        cardLayout.show(cardPanel, panels(n)._1)
}

class DoubleColumnPanel(left: Component, right: Component) extends JPanel {
    setLayout(new BorderLayout())
    add(left, BorderLayout.WEST)
    add(right, BorderLayout.EAST)
}
