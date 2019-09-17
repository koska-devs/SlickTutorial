package dto
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.PostgresProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Memo.schema ++ PlayEvolutions.schema ++ Tag.schema ++ Tagmemo.schema ++ User.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Memo
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param content Database column content SqlType(text)
   *  @param userId Database column user_id SqlType(int4) */
  case class MemoRow(id: Int, content: String, userId: Int)
  /** GetResult implicit for fetching MemoRow objects using plain SQL queries */
  implicit def GetResultMemoRow(implicit e0: GR[Int], e1: GR[String]): GR[MemoRow] = GR{
    prs => import prs._
    MemoRow.tupled((<<[Int], <<[String], <<[Int]))
  }
  /** Table description of table memo. Objects of this class serve as prototypes for rows in queries. */
  class Memo(_tableTag: Tag) extends profile.api.Table[MemoRow](_tableTag, "memo") {
    def * = (id, content, userId) <> (MemoRow.tupled, MemoRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(content), Rep.Some(userId))).shaped.<>({r=>import r._; _1.map(_=> MemoRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column content SqlType(text) */
    val content: Rep[String] = column[String]("content")
    /** Database column user_id SqlType(int4) */
    val userId: Rep[Int] = column[Int]("user_id")

    /** Foreign key referencing User (database name memo_user_id_fkey) */
    lazy val userFk = foreignKey("memo_user_id_fkey", userId, User)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Memo */
  lazy val Memo = new TableQuery(tag => new Memo(tag))

  /** Entity class storing rows of table PlayEvolutions
   *  @param id Database column id SqlType(int4), PrimaryKey
   *  @param hash Database column hash SqlType(varchar), Length(255,true)
   *  @param appliedAt Database column applied_at SqlType(timestamp)
   *  @param applyScript Database column apply_script SqlType(text), Default(None)
   *  @param revertScript Database column revert_script SqlType(text), Default(None)
   *  @param state Database column state SqlType(varchar), Length(255,true), Default(None)
   *  @param lastProblem Database column last_problem SqlType(text), Default(None) */
  case class PlayEvolutionsRow(id: Int, hash: String, appliedAt: java.sql.Timestamp, applyScript: Option[String] = None, revertScript: Option[String] = None, state: Option[String] = None, lastProblem: Option[String] = None)
  /** GetResult implicit for fetching PlayEvolutionsRow objects using plain SQL queries */
  implicit def GetResultPlayEvolutionsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Option[String]]): GR[PlayEvolutionsRow] = GR{
    prs => import prs._
    PlayEvolutionsRow.tupled((<<[Int], <<[String], <<[java.sql.Timestamp], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table play_evolutions. Objects of this class serve as prototypes for rows in queries. */
  class PlayEvolutions(_tableTag: Tag) extends profile.api.Table[PlayEvolutionsRow](_tableTag, "play_evolutions") {
    def * = (id, hash, appliedAt, applyScript, revertScript, state, lastProblem) <> (PlayEvolutionsRow.tupled, PlayEvolutionsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(hash), Rep.Some(appliedAt), applyScript, revertScript, state, lastProblem)).shaped.<>({r=>import r._; _1.map(_=> PlayEvolutionsRow.tupled((_1.get, _2.get, _3.get, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(int4), PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.PrimaryKey)
    /** Database column hash SqlType(varchar), Length(255,true) */
    val hash: Rep[String] = column[String]("hash", O.Length(255,varying=true))
    /** Database column applied_at SqlType(timestamp) */
    val appliedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("applied_at")
    /** Database column apply_script SqlType(text), Default(None) */
    val applyScript: Rep[Option[String]] = column[Option[String]]("apply_script", O.Default(None))
    /** Database column revert_script SqlType(text), Default(None) */
    val revertScript: Rep[Option[String]] = column[Option[String]]("revert_script", O.Default(None))
    /** Database column state SqlType(varchar), Length(255,true), Default(None) */
    val state: Rep[Option[String]] = column[Option[String]]("state", O.Length(255,varying=true), O.Default(None))
    /** Database column last_problem SqlType(text), Default(None) */
    val lastProblem: Rep[Option[String]] = column[Option[String]]("last_problem", O.Default(None))
  }
  /** Collection-like TableQuery object for table PlayEvolutions */
  lazy val PlayEvolutions = new TableQuery(tag => new PlayEvolutions(tag))

  /** Entity class storing rows of table Tag
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(int4) */
  case class TagRow(id: Int, name: Int)
  /** GetResult implicit for fetching TagRow objects using plain SQL queries */
  implicit def GetResultTagRow(implicit e0: GR[Int]): GR[TagRow] = GR{
    prs => import prs._
    TagRow.tupled((<<[Int], <<[Int]))
  }
  /** Table description of table tag. Objects of this class serve as prototypes for rows in queries. */
  class Tag(_tableTag: Tag) extends profile.api.Table[TagRow](_tableTag, "tag") {
    def * = (id, name) <> (TagRow.tupled, TagRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(name))).shaped.<>({r=>import r._; _1.map(_=> TagRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(int4) */
    val name: Rep[Int] = column[Int]("name")
  }
  /** Collection-like TableQuery object for table Tag */
  lazy val Tag = new TableQuery(tag => new Tag(tag))

  /** Entity class storing rows of table Tagmemo
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param memoId Database column memo_id SqlType(int4)
   *  @param tagId Database column tag_id SqlType(int4) */
  case class TagmemoRow(id: Int, memoId: Int, tagId: Int)
  /** GetResult implicit for fetching TagmemoRow objects using plain SQL queries */
  implicit def GetResultTagmemoRow(implicit e0: GR[Int]): GR[TagmemoRow] = GR{
    prs => import prs._
    TagmemoRow.tupled((<<[Int], <<[Int], <<[Int]))
  }
  /** Table description of table tagmemo. Objects of this class serve as prototypes for rows in queries. */
  class Tagmemo(_tableTag: Tag) extends profile.api.Table[TagmemoRow](_tableTag, "tagmemo") {
    def * = (id, memoId, tagId) <> (TagmemoRow.tupled, TagmemoRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(memoId), Rep.Some(tagId))).shaped.<>({r=>import r._; _1.map(_=> TagmemoRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column memo_id SqlType(int4) */
    val memoId: Rep[Int] = column[Int]("memo_id")
    /** Database column tag_id SqlType(int4) */
    val tagId: Rep[Int] = column[Int]("tag_id")

    /** Foreign key referencing Memo (database name tagmemo_memo_id_fkey) */
    lazy val memoFk = foreignKey("tagmemo_memo_id_fkey", memoId, Memo)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Tag (database name tagmemo_tag_id_fkey) */
    lazy val tagFk = foreignKey("tagmemo_tag_id_fkey", tagId, Tag)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Tagmemo */
  lazy val Tagmemo = new TableQuery(tag => new Tagmemo(tag))

  /** Entity class storing rows of table User
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(text) */
  case class UserRow(id: Int, name: String)
  /** GetResult implicit for fetching UserRow objects using plain SQL queries */
  implicit def GetResultUserRow(implicit e0: GR[Int], e1: GR[String]): GR[UserRow] = GR{
    prs => import prs._
    UserRow.tupled((<<[Int], <<[String]))
  }
  /** Table description of table user. Objects of this class serve as prototypes for rows in queries. */
  class User(_tableTag: Tag) extends profile.api.Table[UserRow](_tableTag, "user") {
    def * = (id, name) <> (UserRow.tupled, UserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(name))).shaped.<>({r=>import r._; _1.map(_=> UserRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(text) */
    val name: Rep[String] = column[String]("name")
  }
  /** Collection-like TableQuery object for table User */
  lazy val User = new TableQuery(tag => new User(tag))
}
