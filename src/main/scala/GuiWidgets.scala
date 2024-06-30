import javax.swing._
import javax.swing.border._
import java.awt._


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


class NamedFieldPanel(fields: Seq[String | (String, Component)]) extends JPanel {

    setLayout(new GridLayout(0, 2))
    setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8))

    val inputs = fields.map{ 
        case text: String =>
            add(new JLabel(text))
            val textField = new JTextField(8)
            add(textField)
            textField
        case (text, component) =>
            add(new JLabel(text))
            add(component)
            component
    }
}

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
