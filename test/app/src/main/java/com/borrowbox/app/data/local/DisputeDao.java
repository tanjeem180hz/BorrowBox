package com.borrowbox.app.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.borrowbox.app.model.Dispute;
import java.util.List;

@Dao
public interface DisputeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Dispute dispute);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Dispute> disputes);

    @Update
    void update(Dispute dispute);

    @Query("SELECT * FROM disputes WHERE id = :id")
    LiveData<Dispute> getDisputeByIdLive(long id);

    @Query("SELECT * FROM disputes WHERE id = :id")
    Dispute getDisputeById(long id);

    @Query("SELECT * FROM disputes ORDER BY createdAt DESC")
    LiveData<List<Dispute>> getAllDisputesLive();

    @Query("SELECT * FROM disputes ORDER BY createdAt DESC")
    List<Dispute> getAllDisputes();

    @Query("SELECT * FROM disputes WHERE status = :status ORDER BY createdAt DESC")
    LiveData<List<Dispute>> getDisputesByStatusLive(String status);

    @Query("SELECT COUNT(*) FROM disputes WHERE status IN ('SUBMITTED', 'UNDER_REVIEW')")
    LiveData<Integer> getOpenDisputesCountLive();

    @Query("SELECT COUNT(*) FROM disputes WHERE status = 'RESOLVED'")
    LiveData<Integer> getResolvedDisputesCountLive();

    @Query("SELECT COUNT(*) FROM disputes")
    LiveData<Integer> getTotalDisputesCountLive();

    @Query("UPDATE disputes SET status = :status, adminVerdict = :verdict, depositRefundPercentage = :refundPercent, resolvedAt = :resolvedAt WHERE id = :disputeId")
    void resolveDispute(long disputeId, String status, String verdict, int refundPercent, long resolvedAt);
}
